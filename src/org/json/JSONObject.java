package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

public class JSONObject {

	private static final class Null {

		@Override
		protected final Object clone() {
			return this;
		}

		@Override
		public boolean equals(Object object) {
			return object == null || object == this;
		}

		public String toString() {
			return "null";
		}
	}

	private final Map<String, Object> map;

	public static final Object NULL = new Null();

	public JSONObject() {
		this.map = new HashMap<String, Object>();
	}

	public JSONObject(JSONObject jo, String[] names) {
		this();
		for (int i = 0; i < names.length; i += 1) {
			try {
				this.putOnce(names[i], jo.opt(names[i]));
			} catch (Exception ignore) {
			}
		}
	}

	public JSONObject(JSONTokener x) throws JSONException {
		this();
		char c;
		String key;

		if (x.nextClean() != '{') {
			throw x.syntaxError("A JSONObject text must begin with '{'");
		}
		for (;;) {
			c = x.nextClean();
			switch (c) {
			case 0:
				throw x.syntaxError("A JSONObject text must end with '}'");
			case '}':
				return;
			default:
				x.back();
				key = x.nextValue().toString();
			}

			c = x.nextClean();
			if (c != ':') {
				throw x.syntaxError("Expected a ':' after a key");
			}
			this.putOnce(key, x.nextValue());

			switch (x.nextClean()) {
			case ';':
			case ',':
				if (x.nextClean() == '}') {
					return;
				}
				x.back();
				break;
			case '}':
				return;
			default:
				throw x.syntaxError("Expected a ',' or '}'");
			}
		}
	}

	public JSONObject(Map<String, Object> map) {
		this.map = new HashMap<String, Object>();
		if (map != null) {
			Iterator<Entry<String, Object>> i = map.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				Object value = entry.getValue();
				if (value != null) {
					this.map.put(entry.getKey(), wrap(value));
				}
			}
		}
	}

	public JSONObject(Object bean) {
		this();
		this.populateMap(bean);
	}

	public JSONObject(Object object, String names[]) {
		this();
		Class<?> c = object.getClass();
		for (int i = 0; i < names.length; i += 1) {
			String name = names[i];
			try {
				this.putOpt(name, c.getField(name).get(object));
			} catch (Exception ignore) {
			}
		}
	}

	public JSONObject(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	public JSONObject(String baseName, Locale locale) throws JSONException {
		this();
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale,
				Thread.currentThread().getContextClassLoader());

		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			if (key != null) {

				String[] path = ((String) key).split("\\.");
				int last = path.length - 1;
				JSONObject target = this;
				for (int i = 0; i < last; i += 1) {
					String segment = path[i];
					JSONObject nextTarget = target.optJSONObject(segment);
					if (nextTarget == null) {
						nextTarget = new JSONObject();
						target.put(segment, nextTarget);
					}
					target = nextTarget;
				}
				target.put(path[last], bundle.getString((String) key));
			}
		}
	}

	public JSONObject accumulate(String key, Object value) throws JSONException {
		testValidity(value);
		Object object = this.opt(key);
		if (object == null) {
			this.put(key,
					value instanceof JSONArray ? new JSONArray().put(value)
							: value);
		} else if (object instanceof JSONArray) {
			((JSONArray) object).put(value);
		} else {
			this.put(key, new JSONArray().put(object).put(value));
		}
		return this;
	}

	public JSONObject append(String key, Object value) throws JSONException {
		testValidity(value);
		Object object = this.opt(key);
		if (object == null) {
			this.put(key, new JSONArray().put(value));
		} else if (object instanceof JSONArray) {
			this.put(key, ((JSONArray) object).put(value));
		} else {
			throw new JSONException("JSONObject[" + key
					+ "] is not a JSONArray.");
		}
		return this;
	}

	public static String doubleToString(double d) {
		if (Double.isInfinite(d) || Double.isNaN(d)) {
			return "null";
		}

		String string = Double.toString(d);
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0
				&& string.indexOf('E') < 0) {
			while (string.endsWith("0")) {
				string = string.substring(0, string.length() - 1);
			}
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	public Object get(String key) throws JSONException {
		if (key == null) {
			throw new JSONException("Null key.");
		}
		Object object = this.opt(key);
		if (object == null) {
			throw new JSONException("JSONObject[" + quote(key) + "] not found.");
		}
		return object;
	}

	public <E extends Enum<E>> E getEnum(Class<E> clazz, String key)
			throws JSONException {
		E val = optEnum(clazz, key);
		if (val == null) {
			// JSONException should really take a throwable argument.
			// If it did, I would re-implement this with the Enum.valueOf
			// method and place any thrown exception in the JSONException
			throw new JSONException("JSONObject[" + quote(key)
					+ "] is not an enum of type "
					+ quote(clazz.getSimpleName()) + ".");
		}
		return val;
	}

	public boolean getBoolean(String key) throws JSONException {
		Object object = this.get(key);
		if (object.equals(Boolean.FALSE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("false"))) {
			return false;
		} else if (object.equals(Boolean.TRUE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("true"))) {
			return true;
		}
		throw new JSONException("JSONObject[" + quote(key)
				+ "] is not a Boolean.");
	}

	public BigInteger getBigInteger(String key) throws JSONException {
		Object object = this.get(key);
		try {
			return new BigInteger(object.toString());
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key)
					+ "] could not be converted to BigInteger.");
		}
	}

	public BigDecimal getBigDecimal(String key) throws JSONException {
		Object object = this.get(key);
		try {
			return new BigDecimal(object.toString());
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key)
					+ "] could not be converted to BigDecimal.");
		}
	}

	public double getDouble(String key) throws JSONException {
		Object object = this.get(key);
		try {
			return object instanceof Number ? ((Number) object).doubleValue()
					: Double.parseDouble((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key)
					+ "] is not a number.");
		}
	}

	public int getInt(String key) throws JSONException {
		Object object = this.get(key);
		try {
			return object instanceof Number ? ((Number) object).intValue()
					: Integer.parseInt((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key)
					+ "] is not an int.");
		}
	}

	public JSONArray getJSONArray(String key) throws JSONException {
		Object object = this.get(key);
		if (object instanceof JSONArray) {
			return (JSONArray) object;
		}
		throw new JSONException("JSONObject[" + quote(key)
				+ "] is not a JSONArray.");
	}

	public JSONObject getJSONObject(String key) throws JSONException {
		Object object = this.get(key);
		if (object instanceof JSONObject) {
			return (JSONObject) object;
		}
		throw new JSONException("JSONObject[" + quote(key)
				+ "] is not a JSONObject.");
	}

	public long getLong(String key) throws JSONException {
		Object object = this.get(key);
		try {
			return object instanceof Number ? ((Number) object).longValue()
					: Long.parseLong((String) object);
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key)
					+ "] is not a long.");
		}
	}

	public static String[] getNames(JSONObject jo) {
		int length = jo.length();
		if (length == 0) {
			return null;
		}
		Iterator<String> iterator = jo.keys();
		String[] names = new String[length];
		int i = 0;
		while (iterator.hasNext()) {
			names[i] = iterator.next();
			i += 1;
		}
		return names;
	}

	public static String[] getNames(Object object) {
		if (object == null) {
			return null;
		}
		Class<?> klass = object.getClass();
		Field[] fields = klass.getFields();
		int length = fields.length;
		if (length == 0) {
			return null;
		}
		String[] names = new String[length];
		for (int i = 0; i < length; i += 1) {
			names[i] = fields[i].getName();
		}
		return names;
	}

	public String getString(String key) throws JSONException {
		Object object = this.get(key);
		if (object instanceof String) {
			return (String) object;
		}
		throw new JSONException("JSONObject[" + quote(key) + "] not a string.");
	}

	public boolean has(String key) {
		return this.map.containsKey(key);
	}

	public JSONObject increment(String key) throws JSONException {
		Object value = this.opt(key);
		if (value == null) {
			this.put(key, 1);
		} else if (value instanceof BigInteger) {
			this.put(key, ((BigInteger) value).add(BigInteger.ONE));
		} else if (value instanceof BigDecimal) {
			this.put(key, ((BigDecimal) value).add(BigDecimal.ONE));
		} else if (value instanceof Integer) {
			this.put(key, (Integer) value + 1);
		} else if (value instanceof Long) {
			this.put(key, (Long) value + 1);
		} else if (value instanceof Double) {
			this.put(key, (Double) value + 1);
		} else if (value instanceof Float) {
			this.put(key, (Float) value + 1);
		} else {
			throw new JSONException("Unable to increment [" + quote(key) + "].");
		}
		return this;
	}

	public boolean isNull(String key) {
		return JSONObject.NULL.equals(this.opt(key));
	}

	public Iterator<String> keys() {
		return this.keySet().iterator();
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public int length() {
		return this.map.size();
	}

	public JSONArray names() {
		JSONArray ja = new JSONArray();
		Iterator<String> keys = this.keys();
		while (keys.hasNext()) {
			ja.put(keys.next());
		}
		return ja.length() == 0 ? null : ja;
	}

	public static String numberToString(Number number) throws JSONException {
		if (number == null) {
			throw new JSONException("Null pointer");
		}
		testValidity(number);

		String string = number.toString();
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0
				&& string.indexOf('E') < 0) {
			while (string.endsWith("0")) {
				string = string.substring(0, string.length() - 1);
			}
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	public Object opt(String key) {
		return key == null ? null : this.map.get(key);
	}

	public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
		return this.optEnum(clazz, key, null);
	}

	public <E extends Enum<E>> E optEnum(Class<E> clazz, String key,
			E defaultValue) {
		try {
			Object val = this.opt(key);
			if (NULL.equals(val)) {
				return defaultValue;
			}
			if (clazz.isAssignableFrom(val.getClass())) {
				// we just checked it!
				@SuppressWarnings("unchecked")
				E myE = (E) val;
				return myE;
			}
			return Enum.valueOf(clazz, val.toString());
		} catch (IllegalArgumentException | NullPointerException e) {
			return defaultValue;
		}
	}

	public boolean optBoolean(String key) {
		return this.optBoolean(key, false);
	}

	public boolean optBoolean(String key, boolean defaultValue) {
		try {
			return this.getBoolean(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double optDouble(String key) {
		return this.optDouble(key, Double.NaN);
	}

	public BigInteger optBigInteger(String key, BigInteger defaultValue) {
		try {
			return this.getBigInteger(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
		try {
			return this.getBigDecimal(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double optDouble(String key, double defaultValue) {
		try {
			return this.getDouble(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int optInt(String key) {
		return this.optInt(key, 0);
	}

	public int optInt(String key, int defaultValue) {
		try {
			return this.getInt(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public JSONArray optJSONArray(String key) {
		Object o = this.opt(key);
		return o instanceof JSONArray ? (JSONArray) o : null;
	}

	public JSONObject optJSONObject(String key) {
		Object object = this.opt(key);
		return object instanceof JSONObject ? (JSONObject) object : null;
	}

	public long optLong(String key) {
		return this.optLong(key, 0);
	}

	public long optLong(String key, long defaultValue) {
		try {
			return this.getLong(key);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String optString(String key) {
		return this.optString(key, "");
	}

	public String optString(String key, String defaultValue) {
		Object object = this.opt(key);
		return NULL.equals(object) ? defaultValue : object.toString();
	}

	private void populateMap(Object bean) {
		Class<?> klass = bean.getClass();

		boolean includeSuperClass = klass.getClassLoader() != null;

		Method[] methods = includeSuperClass ? klass.getMethods() : klass
				.getDeclaredMethods();
		for (int i = 0; i < methods.length; i += 1) {
			try {
				Method method = methods[i];
				if (Modifier.isPublic(method.getModifiers())) {
					String name = method.getName();
					String key = "";
					if (name.startsWith("get")) {
						if ("getClass".equals(name)
								|| "getDeclaringClass".equals(name)) {
							key = "";
						} else {
							key = name.substring(3);
						}
					} else if (name.startsWith("is")) {
						key = name.substring(2);
					}
					if (key.length() > 0
							&& Character.isUpperCase(key.charAt(0))
							&& method.getParameterTypes().length == 0) {
						if (key.length() == 1) {
							key = key.toLowerCase();
						} else if (!Character.isUpperCase(key.charAt(1))) {
							key = key.substring(0, 1).toLowerCase()
									+ key.substring(1);
						}

						Object result = method.invoke(bean, (Object[]) null);
						if (result != null) {
							this.map.put(key, wrap(result));
						}
					}
				}
			} catch (Exception ignore) {
			}
		}
	}

	public JSONObject put(String key, boolean value) throws JSONException {
		this.put(key, value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	public JSONObject put(String key, Collection<Object> value)
			throws JSONException {
		this.put(key, new JSONArray(value));
		return this;
	}

	public JSONObject put(String key, double value) throws JSONException {
		this.put(key, new Double(value));
		return this;
	}

	public JSONObject put(String key, int value) throws JSONException {
		this.put(key, new Integer(value));
		return this;
	}

	public JSONObject put(String key, long value) throws JSONException {
		this.put(key, new Long(value));
		return this;
	}

	public JSONObject put(String key, Map<String, Object> value)
			throws JSONException {
		this.put(key, new JSONObject(value));
		return this;
	}

	public JSONObject put(String key, Object value) throws JSONException {
		if (key == null) {
			throw new NullPointerException("Null key.");
		}
		if (value != null) {
			testValidity(value);
			this.map.put(key, value);
		} else {
			this.remove(key);
		}
		return this;
	}

	public JSONObject putOnce(String key, Object value) throws JSONException {
		if (key != null && value != null) {
			if (this.opt(key) != null) {
				throw new JSONException("Duplicate key \"" + key + "\"");
			}
			this.put(key, value);
		}
		return this;
	}

	public JSONObject putOpt(String key, Object value) throws JSONException {
		if (key != null && value != null) {
			this.put(key, value);
		}
		return this;
	}

	public static String quote(String string) {
		StringWriter sw = new StringWriter();
		synchronized (sw.getBuffer()) {
			try {
				return quote(string, sw).toString();
			} catch (IOException ignored) {
				return "";
			}
		}
	}

	public static Writer quote(String string, Writer w) throws IOException {
		if (string == null || string.length() == 0) {
			w.write("\"\"");
			return w;
		}

		char b;
		char c = 0;
		String hhhh;
		int i;
		int len = string.length();

		w.write('"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				w.write('\\');
				w.write(c);
				break;
			case '/':
				if (b == '<') {
					w.write('\\');
				}
				w.write(c);
				break;
			case '\b':
				w.write("\\b");
				break;
			case '\t':
				w.write("\\t");
				break;
			case '\n':
				w.write("\\n");
				break;
			case '\f':
				w.write("\\f");
				break;
			case '\r':
				w.write("\\r");
				break;
			default:
				if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
						|| (c >= '\u2000' && c < '\u2100')) {
					w.write("\\u");
					hhhh = Integer.toHexString(c);
					w.write("0000", 0, 4 - hhhh.length());
					w.write(hhhh);
				} else {
					w.write(c);
				}
			}
		}
		w.write('"');
		return w;
	}

	public Object remove(String key) {
		return this.map.remove(key);
	}

	public boolean similar(Object other) {
		try {
			if (!(other instanceof JSONObject)) {
				return false;
			}
			Set<String> set = this.keySet();
			if (!set.equals(((JSONObject) other).keySet())) {
				return false;
			}
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				Object valueThis = this.get(name);
				Object valueOther = ((JSONObject) other).get(name);
				if (valueThis instanceof JSONObject) {
					if (!((JSONObject) valueThis).similar(valueOther)) {
						return false;
					}
				} else if (valueThis instanceof JSONArray) {
					if (!((JSONArray) valueThis).similar(valueOther)) {
						return false;
					}
				} else if (!valueThis.equals(valueOther)) {
					return false;
				}
			}
			return true;
		} catch (Throwable exception) {
			return false;
		}
	}

	public static Object stringToValue(String string) {
		Double d;
		if (string.equals("")) {
			return string;
		}
		if (string.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		}
		if (string.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		}
		if (string.equalsIgnoreCase("null")) {
			return JSONObject.NULL;
		}

		char b = string.charAt(0);
		if ((b >= '0' && b <= '9') || b == '-') {
			try {
				if (string.indexOf('.') > -1 || string.indexOf('e') > -1
						|| string.indexOf('E') > -1) {
					d = Double.valueOf(string);
					if (!d.isInfinite() && !d.isNaN()) {
						return d;
					}
				} else {
					Long myLong = new Long(string);
					if (string.equals(myLong.toString())) {
						if (myLong == myLong.intValue()) {
							return myLong.intValue();
						} else {
							return myLong;
						}
					}
				}
			} catch (Exception ignore) {
			}
		}
		return string;
	}

	public static void testValidity(Object o) throws JSONException {
		if (o != null) {
			if (o instanceof Double) {
				if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
					throw new JSONException(
							"JSON does not allow non-finite numbers.");
				}
			} else if (o instanceof Float) {
				if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
					throw new JSONException(
							"JSON does not allow non-finite numbers.");
				}
			}
		}
	}

	public JSONArray toJSONArray(JSONArray names) throws JSONException {
		if (names == null || names.length() == 0) {
			return null;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < names.length(); i += 1) {
			ja.put(this.opt(names.getString(i)));
		}
		return ja;
	}

	public String toString() {
		try {
			return this.toString(0);
		} catch (Exception e) {
			return null;
		}
	}

	public String toString(int indentFactor) throws JSONException {
		StringWriter w = new StringWriter();
		synchronized (w.getBuffer()) {
			return this.write(w, indentFactor, 0).toString();
		}
	}

	public static String valueToString(Object value) throws JSONException {
		if (value == null || value.equals(null)) {
			return "null";
		}
		if (value instanceof JSONString) {
			Object object;
			try {
				object = ((JSONString) value).toJSONString();
			} catch (Exception e) {
				throw new JSONException(e);
			}
			if (object instanceof String) {
				return (String) object;
			}
			throw new JSONException("Bad value from toJSONString: " + object);
		}
		if (value instanceof Number) {
			return numberToString((Number) value);
		}
		if (value instanceof Boolean || value instanceof JSONObject
				|| value instanceof JSONArray) {
			return value.toString();
		}
		if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			return new JSONObject(map).toString();
		}
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> coll = (Collection<Object>) value;
			return new JSONArray(coll).toString();
		}
		if (value.getClass().isArray()) {
			return new JSONArray(value).toString();
		}
		return quote(value.toString());
	}

	public static Object wrap(Object object) {
		try {
			if (object == null) {
				return NULL;
			}
			if (object instanceof JSONObject || object instanceof JSONArray
					|| NULL.equals(object) || object instanceof JSONString
					|| object instanceof Byte || object instanceof Character
					|| object instanceof Short || object instanceof Integer
					|| object instanceof Long || object instanceof Boolean
					|| object instanceof Float || object instanceof Double
					|| object instanceof String || object instanceof BigInteger
					|| object instanceof BigDecimal) {
				return object;
			}

			if (object instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<Object> coll = (Collection<Object>) object;
				return new JSONArray(coll);
			}
			if (object.getClass().isArray()) {
				return new JSONArray(object);
			}
			if (object instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) object;
				return new JSONObject(map);
			}
			Package objectPackage = object.getClass().getPackage();
			String objectPackageName = objectPackage != null ? objectPackage
					.getName() : "";
			if (objectPackageName.startsWith("java.")
					|| objectPackageName.startsWith("javax.")
					|| object.getClass().getClassLoader() == null) {
				return object.toString();
			}
			return new JSONObject(object);
		} catch (Exception exception) {
			return null;
		}
	}

	public Writer write(Writer writer) throws JSONException {
		return this.write(writer, 0, 0);
	}

	static final Writer writeValue(Writer writer, Object value,
			int indentFactor, int indent) throws JSONException, IOException {
		if (value == null || value.equals(null)) {
			writer.write("null");
		} else if (value instanceof JSONObject) {
			((JSONObject) value).write(writer, indentFactor, indent);
		} else if (value instanceof JSONArray) {
			((JSONArray) value).write(writer, indentFactor, indent);
		} else if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			new JSONObject(map).write(writer, indentFactor, indent);
		} else if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> coll = (Collection<Object>) value;
			new JSONArray(coll).write(writer, indentFactor, indent);
		} else if (value.getClass().isArray()) {
			new JSONArray(value).write(writer, indentFactor, indent);
		} else if (value instanceof Number) {
			writer.write(numberToString((Number) value));
		} else if (value instanceof Boolean) {
			writer.write(value.toString());
		} else if (value instanceof JSONString) {
			Object o;
			try {
				o = ((JSONString) value).toJSONString();
			} catch (Exception e) {
				throw new JSONException(e);
			}
			writer.write(o != null ? o.toString() : quote(value.toString()));
		} else {
			quote(value.toString(), writer);
		}
		return writer;
	}

	static final void indent(Writer writer, int indent) throws IOException {
		for (int i = 0; i < indent; i += 1) {
			writer.write(' ');
		}
	}

	Writer write(Writer writer, int indentFactor, int indent)
			throws JSONException {
		try {
			boolean commanate = false;
			final int length = this.length();
			Iterator<String> keys = this.keys();
			writer.write('{');

			if (length == 1) {
				Object key = keys.next();
				writer.write(quote(key.toString()));
				writer.write(':');
				if (indentFactor > 0) {
					writer.write(' ');
				}
				writeValue(writer, this.map.get(key), indentFactor, indent);
			} else if (length != 0) {
				final int newindent = indent + indentFactor;
				while (keys.hasNext()) {
					Object key = keys.next();
					if (commanate) {
						writer.write(',');
					}
					if (indentFactor > 0) {
						writer.write('\n');
					}
					indent(writer, newindent);
					writer.write(quote(key.toString()));
					writer.write(':');
					if (indentFactor > 0) {
						writer.write(' ');
					}
					writeValue(writer, this.map.get(key), indentFactor,
							newindent);
					commanate = true;
				}
				if (indentFactor > 0) {
					writer.write('\n');
				}
				indent(writer, indent);
			}
			writer.write('}');
			return writer;
		} catch (IOException exception) {
			throw new JSONException(exception);
		}
	}
}

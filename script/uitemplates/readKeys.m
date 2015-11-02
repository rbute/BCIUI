
function settings=readKeys(filepath)

settings=JSON.parse(fileread(filepath));
key=settings.keys;
settings=key;
end
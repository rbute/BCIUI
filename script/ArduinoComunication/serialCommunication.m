s=serial('COM4');
set(s,'BaudRate',9600);
fopen(s);
fprintf(s,'100 100');
fprintf(s,'-100 -100');
fclose(s);
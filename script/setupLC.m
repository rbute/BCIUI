global gLCApp;
global gChans;
addpath device;
GetLCApp;
gChans = [ 1 2 3 4 5 6 7 8 ];
doc1=gLCApp.create(strcat(pwd,'\documents\new.adicht'));
RegisterLCEvents(doc1);
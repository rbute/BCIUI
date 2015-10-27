global gLCApp;
global gChans;
addpath ImportantForDAQ;
GetLCApp;
gChans = [ 1 ];
doc1=gLCApp.ActiveDocument;
RegisterLCEvents(doc1);
doc1.StartSampling();

pause(8);

doc1.StopSampling();


blockIndex = 0;
blockLen = doc1.GetRecordLength(blockIndex);
secsPerTick = doc1.GetRecordSecsPerTick(blockIndex);
doc1.SelectChannel(1,1); %Turn on selection in Channel 2
%Select last 5 seconds in first block
doc1.SetSelectionRange(blockIndex,blockLen-5.0/secsPerTick,blockIndex,blockLen);
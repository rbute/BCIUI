clear all;
global gLCApp;
global gChans;
global gLCDoc;
addpath device;
%GetLCApp;
gChans = [ 1 2 3 4 ];

if isempty(gLCApp) | not(gLCApp.isinterface)
gLCDoc = actxserver('ADIChart.Document');
gLCApp = gLCDoc.Application;   
end 

gLCDoc.registerevent({
    'OnStartSamplingBlock' LCCallBacks('OnBlockStart'); 
    'OnNewSamples' LCCallBacks('OnNewSamples');
    'OnFinishSamplingBlock' LCCallBacks('OnBlockFinish')
    'OnSelectionChange' LCCallBacks('OnSelectionChange')
    });

% gLCDoc.numberOfDisplayedChannels = 4;
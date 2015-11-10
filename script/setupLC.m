clear all;

disp('Seting up Lab Chart')

global gLCApp;
global gChans;
global gLCDoc;

global gLatestBlock;
global gBlockSecsPerTick;
global gLatestTickInBlock;
global gChansData;
global gT;

global SSVEP_CHANS;

addpath device;
addpath JSON;
addpath uitemplates;
%GetLCApp;
gChans = [ 1 2 3 ];



SSVEP_CHANS = gChans;

if isempty(gLCApp) | not(gLCApp.isinterface)
gLCDoc = actxserver('ADIChart.Document');
gLCApp = gLCDoc.Application;   
else
gLCApp.CloseActiveDocument(true);
gLCDoc = actxserver('ADIChart.Document');
end 

gLCDoc.registerevent({
    'OnStartSamplingBlock' LCCallBacks('OnBlockStart'); 
    'OnNewSamples' LCCallBacks('OnNewSamples');
    'OnFinishSamplingBlock' LCCallBacks('OnBlockFinish')
    'OnSelectionChange' LCCallBacks('OnSelectionChange')
    });

% gLCDoc.numberOfDisplayedChannels = 4;
clear gLCDoc settings gLCApp gChans gLatestBlock gBlockSecsPerTick ...
    gLatestTickInBlock gChansData gT datafolder samplingStartTime ...
    SSVEP_CHANS spect;

disp('Seting up Lab Chart')



global gLCDoc 
global settings 
global gLCApp 
global gChans 

global gLatestBlock 
global gBlockSecsPerTick 
global gLatestTickInBlock 
global gChansData 
global gT 

global datafolder
global samplingStartTime
global SSVEP_CHANS
global spect;

%addpath device;
%addpath JSON;
%addpath uitemplates;
%addpath util;
%GetLCApp;
gChans = 1:8;



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
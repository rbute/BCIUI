

% For Lab chart application and it's launching
global gLCDoc 
global settings 
global gLCApp 
global gChans 

% For Acquired data by lab chart
global gLatestBlock 
global gBlockSecsPerTick 
global gLatestTickInBlock 
global gChansData 
global gT 

% For scripted Data processing
global datafolder
global samplingStartTime
global SSVEP_CHANS
global spect
global dataAnalysisInfo;
global dataChannelInfo

load ('.\tests\launchSetup1.mat');

% Pre Experiment inclusion
addpath JSON
addpath util

tmp_jsonData=JSON.parse( fread(fopen(tmp_settingsFile)));
tmp_launchTimeMS = currentTimeMillis;

% Pre Experiment Exclusion
rmpath JSON
rmpath util
% pre Experiment cleaning
clear tmp_settingsFile

do('SETUP',tmp_launchTimeMS,tmp_jsonData);

for i=1:10
    do('START',currentTimeMillis,' ' );
    pause(1.0);
    do('STOP',currentTimeMillis,' ' );
end
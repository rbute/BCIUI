
% featuer of this script
% A quick ready made script for setup of 
% Implementation experiments

% clear SSVEP_CHANS gBlockSecsPerTick gChans...
%     gChansData gLCApp gLCDoc gLatestBlock...
%     gLatestTickInBlock gT settings;

addpath ..
setupLC                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
global datafolder
global samplingStartTime

evalin('base',' global datafolder;');
evalin('base',' global samplingStartTime;');

addpath ..\device;
addpath ..\JSON;
addpath ..\util;
addpath ..\temp;

samplingStartTime=currentTimeMillis;
datafolder=['..\data\',getTimeStamp];

mkdir(datafolder);
addpath(datafolder);
load('exp1_info');
cd ..
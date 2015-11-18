
function [ output_args ] = do( action ,timestamp,data)


%DO Summary of this function goes here
%   Detailed explanation goes her


 global gLCDoc 
 global settings 
 global samplingStartTime
 global spect
 global datafolder
 global gChans ;


if(strcmp(action,'START'))
    %evalin('base','gLCDoc.StartSampling;');
    gLCDoc.StartSampling;
    figure;
    samplingStartTime=currentTimeMillis;
elseif(strcmp (action,'STOP'))
    %evalin('base','gLCDoc.StopSampling;');
    %evalin('base','SSVEP_exp1; ');  
    gLCDoc.StopSampling
    SSVEP_exp1;
    
elseif(strcmp(action,'SETUP'))
   disp('Preparing for experiment.');
   
   addpath JSON;
   %evalin('base','settings=JSON.parse(data);')
   %evalin('base','setupLC;');
   settings=JSON.parse(data);
   setupLC;
   
    %global gLCDoc ;
    %global settings ;
    global gLCApp ;
    %global gChans ;
    global gLatestBlock ;
    global gBlockSecsPerTick ;
    global gLatestTickInBlock ;
    global gChansData ;
    global gT ;
    global SSVEP_CHANS; 
    global dataAnalysisInfo;
    %global datafolder;
    %global spect;
    
    addpath device;
    addpath JSON;
    addpath util;
    
    datafolder=['..\data\',getTimeStamp];
    mkdir(datafolder);
    load('exp1_info');
    
        %setupExp;
end



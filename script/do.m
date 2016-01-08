
function [ output_args ] = do( action ,timestamp,data)


%DO Summary of this function goes here
%   Detailed explanation goes her

% LabLabchart Variables

 

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
global capturedData

global dataAnalysisInfo;
global dataChannelInfo
global myBot
% Start Sampling script
if(strcmp(action,'START'))
    %evalin('base','gLCDoc.StartSampling;');
%     gLCDoc.StartSampling;
%     figure;
    samplingStartTime=timestamp;
    close all;
% Stop Sampling Script
elseif(strcmp (action,'STOP'))
    %evalin('base','gLCDoc.StopSampling;');
    %evalin('base','SSVEP_exp1; ');  
%     gLCDoc.StopSampling
    SSVEP_exp1;
    SSVEP_exp2;
    close all;
% Setup Script
elseif(strcmp(action,'SETUP'))
   disp('Preparing for experiment.');
   
   addpath JSON;
   %evalin('base','settings=JSON.parse(data);')
   %evalin('base','setupLC;');
   settings=JSON.parse(data);
   setupLC;
       
    addpath device;
    addpath JSON;
    addpath util;
    addpath device;
    
%     datafolder=['..\data\',getTimeStamp];
%     mkdir(datafolder);
    load('exp1_info');
    assignin('base','dataAnalysisInfo',dataAnalysisInfo);
    assignin('base','dataChannelInfo',dataChannelInfo);
    
    
        %setupExp;
     %Copy of quick Setup Code
    addpath .
    setupLC                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    %global datafolder
    %global samplingStartTime

    evalin('base',' global datafolder;');
    evalin('base',' global samplingStartTime;');

    samplingStartTime=currentTimeMillis;
    datafolder=['.\data\',getTimeStamp];

    mkdir(datafolder);
    addpath(datafolder);
    load('exp1_info');
    
    try
        myBot = bot('COM12',[],[]);
    catch
    end
   
    
    cd .
end



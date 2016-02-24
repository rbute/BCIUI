
function [ output_args ] = do( action ,timestamp,data)


%DO Summary of this function goes here
%   Detailed explanation goes her

% LabLabchart Variables

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

% For Lab chart application and it's launching
global gLCDoc
global UISettings
global gLCApp
global gChans
global messege_banner
% Start Sampling script
if(strcmp(action,'START'))
    disp(['Acquired Timestamp: ' timestamp])
    gLCDoc.StartSampling; %uncomment later
    %samplingStartTime=timestamp;
    samplingStartTime=currentTimeMillis;
    close all;
    
    % Stop Sampling Script
elseif(strcmp (action,'STOP'))
    gLCDoc.StopSampling
    %     SSVEP_exp1;
    %     SSVEP_exp2; % uncomment later
    disp(['Evaluating: ' UISettings.uioptions.experimentscript]);
    eval(UISettings.uioptions.experimentscript);
    close all;
    
    % elseif (strcmp(action,'SET_MSG_PANE'))
    %     disp('Setting Message Banner')
    %     disp(data)
    %     messege_banner = data;
    
    
    % Setup Script
elseif(strcmp(action,'SETUP'))
    disp('Preparing for experiment.');
    
    addpath device;
    addpath JSON;
    addpath util;
    addpath device;
    
    load('exp1_info');
    %     assignin('base','dataAnalysisInfo',dataAnalysisInfo);
    %     assignin('base','dataChannelInfo',dataChannelInfo);
    %     assignin('base','gChansData',gChansData);
    %     assignin('base','gBlockSecsPerTick',gBlockSecsPerTick);
    
    %Copy of quick Setup Code
    addpath .
    samplingStartTime=num2str(timestamp);
    datafolder=['.\data\',getTimeStamp];
    UISettings=JSON.parse(data);
    load('exp1_info');
    mkdir(datafolder);
    addpath(datafolder);
    load('exp1_info');
    try
        myBot = bot('COM11',{},{'1' '2' '3' '4' '5' ;...
            '1' '3' '0' '4' '2'}');
    catch
    end
    setupLC;
    cd .
end
end



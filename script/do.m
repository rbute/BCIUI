
function [ output_args ] = do( action ,timestamp,data)



%DO Summary of this function goes here
%   Detailed explanation goes her



% global gLCApp;
% global gChans;
 global gLCDoc;
% 
% global gLatestBlock;
% global gBlockSecsPerTick;
% global gLatestTickInBlock;
% global gChansData;
% global gT;
% 
% global SSVEP_CHANS;

if(strcmp(action,'STOP'))
    gLCDoc.StartSampling;
elseif(strcmp (action,'START'))
    gLCDoc.StopSampling;
    SSVEP_exp1;   
    
elseif(strcmp(action,'SETUP'))
    setupLC;
    setupExp;
end
end


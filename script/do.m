
function [ output_args ] = do( action ,timestamp,data)


%DO Summary of this function goes here
%   Detailed explanation goes her


% global gLCDoc 
% global settings 
% global gLCApp 
% global gChans 
% 
% global gLatestBlock 
% global gBlockSecsPerTick 
% global gLatestTickInBlock 
% global gChansData 
% global gT 
% 
% global SSVEP_CHANS



if(strcmp(action,'STOP'))
    evalin('base','gLCDoc.StartSampling;');
elseif(strcmp (action,'START'))
    evalin('base','gLCDoc.StopSampling;');
    evalin('base','SSVEP_exp1; ');  
    
elseif(strcmp(action,'SETUP'))
   disp('Preparing for experiment.');
   addpath JSON;
   evalin('base','settings=JSON.parse(data);')
   evalin('base','setupLC;');
        %setupExp;
end



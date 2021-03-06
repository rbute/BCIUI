

% close(gcf);
% close(gcf);


standaloneExp=[];

if(~exist('database','var') | exist ('standaloneExp','var'))
    database=uigetdir('..\data','Please select a Database');
end

[dbname,foldername,~]=fileparts(database);

global gBlockSecsPerTick
global fftPoints
global f1
global f2
global settings;
global gridrows;
global gridcols;
global tobeplottedchans;
global gridusedupto;
global settingsFilepath
global saveplot

global stimulusFreqs
global freqWindow
% Addpaths
addpath ..\JSON\

% Load Data
load('..\exp1_info');
load([database,'\',foldername,'_data.mat']);
close all;
% Set Experiment Sepecific variables

% capturedData=capturedData(1:6,1:199);

f1 = 5.0;
f2 = 20.0;
gridrows=2;
gridcols=7;
saveplot=true;
gridusedupto=0;
fftPoints = 8196;
tobeplottedchans=1:6;
gBlockSecsPerTick=1e-3;
settingsFilepath = '..\..\settings\robotControl.json';
stimulusFreqs = [7 9 13 15 17];
freqWindow = 1.0;
% capturedData = capturedData(1:200,:);

% Analysis;
% Analysis2;
Analysis3;

if exist('myBot','var')
       myBot.sendCommand(num2str( indx));
end
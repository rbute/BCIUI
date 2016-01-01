

% close(gcf);
% close(gcf);

if(~exist('database','var'))
    database=uigetdir('..\data','Please select a Database');
end

[dbname,foldername,~]=fileparts(database);
replot=false;

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
fftPoints = 5000;
tobeplottedchans=1:6;
gBlockSecsPerTick=1e-3;
settingsFilepath = '..\..\settings\robotControl.json';
stimulusFreqs = [7 9 13 15];

Plotter;
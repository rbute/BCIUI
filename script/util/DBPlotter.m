

% close(gcf);
% close(gcf);
database=uigetdir('..\data','Please select a Database');
[~,foldername,~]=fileparts(database);

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

% Addpaths
addpath ..\JSON\

% Load Data
load('..\exp1_info');
load([database,'\',foldername,'_data.mat']);

% Set Experiment Sepecific variables

f1 = 5.0;
f2 = 20.0;
gridrows=2;
gridcols=7;
gridusedupto=0;
fftPoints = 5000;
tobeplottedchans=1:6;
gBlockSecsPerTick=1e-3;
settingsFilepath = '..\..\settings\robotControl.json';

Plotter;
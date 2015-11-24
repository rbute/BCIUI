
database=uigetdir('.\data','Please select a Database');
folders=ls(database);
[fl,~]=size(folders);
folders=folders(3:fl,:);
%clear fl;
fl=fl-2;
figure('units','normalized','outerposition',[0 0 1 1]);

global calculationMatrix;
global gBlockSecsPerTick
global fftPoints
global f1
global f2
global settings;

addpath JSON\

fftPoints = 5000;
gBlockSecsPerTick=1e-3;
f1 = 3.0;
f2 = 30.0;
calculationMatrix = ...
    [ -1 -1 -1 -1 0 4 0 0];

gridrows=2;
gridcols=3;
tobeplottedchans=[1 2 3 4 6];
gridusedupto=0;

load('exp1_info');
settings=JSON.parse(fileread(filepath));

try
    calculationMatrix=calmat;
catch
    
end

for i=1:fl
    %disp(['Loading ',folders(i,:)])
    load([database,'\',folders(i,:),'\',folders(i,:),'_data.mat']);
    fplot;
    pause(0.9999);
end
database=uigetdir('..\data','Please select a Database');
% folders=ls(database);
% [fl,~]=size(folders);
% folders=folders(3:fl,:);
%folders=folders(exist([database,'\',folders(:,:)])==7);

%clear fl;
% [fl,~]=size(folders);
close(gcf);
figure('units','normalized','outerposition',[0 0 1 1]);

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

addpath ..\JSON\


load('..\exp1_info');

fftPoints = 5000;
gBlockSecsPerTick=1e-3;
f1 = 5.0;
f2 = 20.0;

gridrows=2;
gridcols=6;
tobeplottedchans=[1 2 3 4 5 6];
gridusedupto=0;


try
    settings=JSON.parse(fileread(filepath));
catch
end

load([database,'\',foldername,'_data.mat']);

%permutation and combination
% capturedData= pca(capturedData)';
funcplot;
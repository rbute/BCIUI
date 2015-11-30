
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
% addpath ..\JSON\

% Load Data
% load('..\exp1_info');
% load([database,'\',foldername,'_data.mat']);

% Set Experiment Sepecific variables
f1 = 5.0;
f2 = 20.0;
gridrows=2;
gridcols=7;
gridusedupto=0;
fftPoints = 5000;
tobeplottedchans=1:6;
gBlockSecsPerTick=1e-3;
% filepath = '..\..\settings\robotControl.json';

% Plot Time domain
[~,N]=size(capturedData);
grid=[3,2];
timeBase=gBlockSecsPerTick*(0:(N-1));

close all

% Unprocessed Waveforms
figure('units','normalized','outerposition',[0 0 1 1]);
groupPlot(capturedData(1:6,:),timeBase,[3,4],1:6,...
    {'Seconds'},{'Voltage (\muV)'}, strcat('Waveform of raw ',...
    {' O_1' ' O_2' ' O_3' ' O_4' ' O_5' ' O_6'},' Channel'));

% PCA Processed waveform 
pcaMat = pca(capturedData(1:6,:)');
analyzedData=(pcaMat*capturedData(1:6,:));
groupPlot(analyzedData,timeBase,[3,4],7:12,...
    {'Seconds'},{'Voltage (\muV)'},strcat('Waveform of PCA procesed ',...
    {' O_1' ' O_2' ' O_3' ' O_4' ' O_5 ' ' O_6'},' Channel'));

% Plot Frequency Domain

%Unprocessed Spectrum
figure('units','normalized','outerposition',[0 0 1 1]);
freqBase = 1/(gBlockSecsPerTick*fftPoints)*(1:(fftPoints-1));
spect=abs(fft(capturedData(1:6,:)',fftPoints,1))';
plotSelection=(freqBase>f1 & freqBase<f2);
groupPlot(spect(:,plotSelection),freqBase(plotSelection),[3,4],1:6,...
    {'Freq (Hz)'},{'Voltage (\muV)'},strcat('Spectrum of' ,...
    {' O_1' ' O_2' ' O_3' ' O_4' ' O_5' ' O_6'},' Channel'));

%PCA Processed Spectrum
analyzedSpect = abs(fft(analyzedData',fftPoints,1))';
groupPlot(analyzedSpect(:,plotSelection),freqBase(plotSelection),[3,4],7:12,...
    {'Freq (Hz)'},{'Voltage (\muV)'},strcat('Spectrum of PCA processed' ,...
    {' O_1' ' O_2' ' O_3' ' O_4' ' O_5' ' O_6'},' Channel'));

% Classification of the signal




% Make Calculations
% try
%     settings=JSON.parse(fileread(settingsFilepath));
% catch
% end
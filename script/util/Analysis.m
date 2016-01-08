
global gBlockSecsPerTick
global fftPoints
global f1
global f2
global settings;
global gridrows;
global gridcols;
global saveplot;
global tobeplottedchans;
global gridusedupto;
global settingsFilepath
global indx;
% Addpaths
% addpath ..\JSON\

% Load Data
% load('..\exp1_info');
% load([database,'\',foldername,'_data.mat']);

% Set Experiment Sepecific variables
% f1 = 5.0;
% f2 = 20.0;
% gridrows=2;
% gridcols=7;
% gridusedupto=0;
% fftPoints = 5000;
% tobeplottedchans=1:6;
% gBlockSecsPerTick=1e-3;
% filepath = '..\..\settings\robotControl.json';

% Plot Time domain
[N,~]=size(capturedData);
grid=[3,2];
timeBase=(gBlockSecsPerTick*(0:(N-1)))';



% Unprocessed Waveforms
% if saveplot
%     saveas(gcf,['plot_prints\',foldername,'_raw-wave'],'jpg')
% end

% PCA Processed waveform
% pcaMat = pca(capturedData(:,1:6));
% analyzedData=(pcaMat*capturedData(:,1:6)')';
analyzedData = capturedData(:,1:6);

% ssvep_mod_mat=get_ssvep_mod_mat([7 9 13 15],1/...
%     gBlockSecsPerTick,N*gBlockSecsPerTick);
ssvep_mod_mat=sin(2*pi*(0:gBlockSecsPerTick:(N-1)...
    *gBlockSecsPerTick)'*[7 9 13 15]);

% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,capturedData(:,1:6));
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
[A1 ,B] = canoncorr(ssvep_mod_mat,analyzedData);
%  max_spect=max(abs(fft(U,fftPoints,1)),[],2);
% max_spect=mean(abs(fft(U,fftPoints,1)),2);
% obs=size(max_spect,1);
% freqs=((1/gBlockSecsPerTick)*(0:(obs-1))/obs)';

% plot(freqs(freqs>3&freqs<20),max_spect(freqs>3&freqs<20));
% xlabel('Freq [Hz]');
%%%%%%%%%%%%%%%%
% r
% A
% B

[~,indx]=max(max(abs(A1),[],2));
disp(['The result is(From Analysis):',num2str( indx)])
% Plot;
botCommand=num2str( indx);
RobotControl;
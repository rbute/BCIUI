
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
pcaMat = pca(capturedData(:,1:6));
analyzedData=(pcaMat*capturedData(:,1:6)')';


% ssvep_mod_mat=get_ssvep_mod_mat([7 9 13 15],1/...
%     gBlockSecsPerTick,N*gBlockSecsPerTick);
ssvep_mod_mat=sin(2*pi*(0:gBlockSecsPerTick:(N-1)...
    *gBlockSecsPerTick)'*[7 9 13 15]);

% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,capturedData(:,1:6));
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
[A ,B] = canoncorr(ssvep_mod_mat,analyzedData);
%  max_spect=max(abs(fft(U,fftPoints,1)),[],2);
% max_spect=mean(abs(fft(U,fftPoints,1)),2);
% obs=size(max_spect,1);
% freqs=((1/gBlockSecsPerTick)*(0:(obs-1))/obs)';

% plot(freqs(freqs>3&freqs<20),max_spect(freqs>3&freqs<20));
% xlabel('Freq [Hz]');

%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%% Plot Waves %%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%

% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Waveform of different Channels');
% groupPlot(capturedData(:,1:6),timeBase,[3,2],1:6,...
%     {'Seconds'},{'Voltage (\muV)'}, strcat('Waveform of raw ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));
% 
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Spectrum of different Channels');
% quickSpect(capturedData(:,1:6),[3 2],5000,1,1000,[4 20],...
%     'Freq [Hz]','Voltage (\muV)',strcat('Spectrum of raw ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));
% 
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Waveform of Correlated U');
% groupPlot(U,timeBase,[3,2],1:6,...
%     {'Seconds'},{'Voltage (\muV)'},strcat('Waveform of Correlated Wave U ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));
% 
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Spectrum of Correlated U');
% quickSpect(U,[3 2],5000,1,1000,[4 20],...
%     'Freq [Hz]','Voltage (\muV)',strcat('Spectrum of Correlated Wave U ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));
% 
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Waveform of Correlated V');
% groupPlot(V,timeBase,[3,2],1:6,...
%     {'Seconds'},{'Voltage (\muV)'},strcat('Waveform of Correlated Wave V ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));
% 
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Spectrum of Correlated V');
% quickSpect(V,[3 2],5000,1,1000,[4 20],...
%     'Freq [Hz]','Voltage (\muV)',strcat('Spectrum of Correlated Wave V ',...
%     {' O_1' ' O_2' ' P_3' ' P_4' ' O_z' ' P_z'},' Channel'));

%%%%%%%%%%%%%%%%
% r
% A
% B

endResult=find(max(abs(A))==max(max(abs(A))));
disp(['The result is:',num2str( endResult)])


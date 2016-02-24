
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
global stimulusFreqs

if exist('gChansData','var')
capturedData=cell2mat(gChansData);
end

disp(['Size of Captured Data: ', num2str(size(capturedData))]);
% Plot Time domain
[N,~]=size(capturedData);
grid=[3,2];
timeBase=(gBlockSecsPerTick*(0:(N-1)))';

% PCA Processed waveform
% pcaMat = pca(capturedData(:,1:6));
% analyzedData=(pcaMat*capturedData(:,1:6)')';
analyzedData = capturedData(:,1:6);

% ssvep_mod_mat=get_ssvep_mod_mat([7 9 13 15],1/...
%     gBlockSecsPerTick,N*gBlockSecsPerTick);

% ssvep_mod_mat=sin(2*pi*(0:gBlockSecsPerTick:(N-1)...
%     *gBlockSecsPerTick)'*[7 9 13 15]);


%Short way to force set
if ~exist('stimulusFreqs','var')
    stimulusFreqs=[7,9,11,13,15];
end

if ~exist('ssvep_mod_mat','var') || size(ssvep_mod_mat,1)<N
    % freqs=[7:2:13];
    ssvep_mod_mat(:,1:2:2*size(stimulusFreqs,2))=...
        sin(2*pi*(0:gBlockSecsPerTick:(N*2-1)*gBlockSecsPerTick)'...
        *stimulusFreqs);
    ssvep_mod_mat(:,2:2:2*size(stimulusFreqs,2))=...
        cos(2*pi*(0:gBlockSecsPerTick:(N*2-1)...
        *gBlockSecsPerTick)'*stimulusFreqs);
end


% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,capturedData(:,1:6));
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
% [A ,B, r, U, V] = canoncorr(ssvep_mod_mat,analyzedData);
[A1 ,B] = canoncorr(ssvep_mod_mat(1:size(analyzedData,1),:),analyzedData);

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

[~,indx]=max(max(abs([A1(1:2:size(A1,1),:) A1(2:2:size(A1,1),:)]),[],2));
disp(['Analysis2: Cannonn Corr Result:',num2str( indx)])

output_args=abs(fft(analyzedData,fftPoints));
[obs,vects]=size(output_args);
freqs=(0:(obs-1))/(obs*gBlockSecsPerTick);
out=output_args*[1 1 -1 -1 -1 1 ]';
plot(freqs(freqs>freqRange(1)&freqs<freqRange(2)),...
            out(freqs>freqRange(1)&freqs<freqRange(2),:));

        
%         freqRange=[4 20];
% Plot;
% indx=4;


% spectrogram()

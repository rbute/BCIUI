
clear all;
load S2_mt
fftpoints = 8196;
% fftrange = 12;
data4analysis = dat.de2;
if exist('fftrange','var')
spect    = abs(fft(data4analysis(1:floor(fftrange*dat.fs),:),fftpoints));
else
spect    = abs(fft(data4analysis,fftpoints));
end
freqBase = linspace(0,512,fftpoints)';

plot(freqBase(freqBase > 0 & freqBase < 30), spect(freqBase > 0 &...
    freqBase < 30,:));
xlabel('Freq (Hz)');
ylabel('Voltage (\muV)');
title(['EEG Spectrum of O_z, freqs: [  ' num2str(dat.tf) '  ]']);
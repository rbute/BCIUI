
%disp(dataChannelInfo)


global gBlockSecsPerTick
global fftPoints
global f1
global f2
global gridrows;
global gridcols;
global tobeplottedchans;
global gridusedupto;

freqs = (1/(gBlockSecsPerTick*fftPoints))*(0:(fftPoints-1));
rangeIndices=find(freqs>f1 & freqs<f2);

hold off;
clf(gcf);
gridusedupto=0;

[~,noOfChannelPlots]=size(tobeplottedchans);

for i = 1:noOfChannelPlots
    gridusedupto=gridusedupto+1;
    subplot(gridrows,gridcols,gridusedupto);
    spect=abs(fft(capturedData(tobeplottedchans(i),:)',250));
    plot(freqs(rangeIndices),spect(rangeIndices));
    title(['Frequency plot of ',strtrim(dataChannelInfo(i,:)),' channel'])
    ylabel('Voltage');
    xlabel('Frequency (Hz)');
    
end

clear noOfChannelPlots 

[ssvep_spect,plotted_freqs]=calc_SSVEP(capturedData(1:6,:));
% [ssvep_spect,plotted_freqs]=calc_SSVEP( pca(capturedData(1:6,:))');

ssvep_spect=ssvep_spect';
plotted_freqs=plotted_freqs';
[noOfPlots,~]=size(ssvep_spect);

for i=1:noOfPlots
    gridusedupto=gridusedupto+1;
    subplot(gridrows,gridcols,gridusedupto);
    plot(plotted_freqs,ssvep_spect(i,:));
    title(['Frequency plot of ',strtrim(dataAnalysisInfo(i,:)),]);
    ylabel('Voltage');
    xlabel('Frequency (Hz)');
end



%clear noOfPLots rangeIndices i gridusedupto  freqs 
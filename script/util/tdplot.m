
%disp(dataChannelInfo)


global gBlockSecsPerTick
global fftPoints
global f1
global f2
global gridrows;
global gridcols;
global tobeplottedchans;
global gridusedupto;



% hold off;
clf(gcf);
gridusedupto=0;

[~,noOfChannelPlots]=size(tobeplottedchans);
[~,N]=size(dataChannelInfo);
% freqs = (1/(gBlockSecsPerTick*fftPoints))*(0:(fftPoints-1));
freqs = gBlockSecsPerTick*(0:(N-1));
% rangeIndices=find(freqs>f1 & freqs<f2);


for i = 1:noOfChannelPlots
    gridusedupto=gridusedupto+1;
    subplot(gridrows,gridcols,gridusedupto);
    spect=capturedData(tobeplottedchans(i),:);
    plot(freqs,spect);
    title(['Signal of ',strtrim(dataChannelInfo(i,:)),' channel'])
    ylabel('Voltage');
    xlabel('Time (Second)');
    
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
    title(['Waveform of ',strtrim(dataAnalysisInfo(i,:)),]);
    ylabel('Voltage');
    xlabel('Frequency (Hz)');
end



%clear noOfPLots rangeIndices i gridusedupto  freqs 
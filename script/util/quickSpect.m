function [ output_args ] = quickSpect( input_args ,subPlots, fftPoints ...
    ,fftDim,Fs,freqRange,xLabel,yLabel,tiTle)
%QUICKSPECT Summary of this function goes here
%  [ output_args ] = quickSpect( input_args ,subPlots, fftPoints ...
%                       ,fftDim,Fs,freqRange,xLabel,yLabel,tiTle)

output_args=abs(fft(input_args,fftPoints,fftDim));
[obs,vects]=size(output_args);
% figure('units','normalized','outerposition',[0 0 1 1],'Name',...
%     'Waveform of PCA Processed Chanels');
freqs=Fs*(0:(obs-1))/obs;

for i=1:vects
    subplot(subPlots(1),subPlots(2),i);
    plot(freqs(freqs>freqRange(1)&freqs<freqRange(2)),...
            output_args(freqs>freqRange(1)&freqs<freqRange(2),i));
    xlabel(xLabel);
    ylabel(yLabel);
    if length(tiTle)==1
        title(tiTle)
    elseif length(tiTle)>1
        title(tiTle{i})
    end
end
end


function [ output_args,plotted_freqs ] = calc_SSVEP( input_args )
%CALC_SSVEP Summary of this function goes here
%   Detailed explanation goes here

global f1
global f2
global gBlockSecsPerTick;
global calculationMatrix;

analysisTimeFFTPoints = 5000;
freqs = (1/(gBlockSecsPerTick*analysisTimeFFTPoints))...
    *(0:(analysisTimeFFTPoints-1));

output_args= abs(fft((calculationMatrix * input_args)',analysisTimeFFTPoints,1));

plotted_freqs=freqs(find(freqs>=f1 & freqs <=f2))';
output_args=output_args(find(freqs>=f1 & freqs <=f2),:);

end


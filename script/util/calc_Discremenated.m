function [ output_args,plotted_freqs ] = calc_Discremenated( input_args )
%CALC_DISCREMENATED Summary of this function goes here
%   Detailed explanation goes here


global gBlockSecsPerTick
global f1
global f2

calculationMatrix = ...
    [  -1 -1 -1 -1 4];

[~,N]=size(input_args);

times = gBlockSecsPerTick*(0:(N-1));

[result]=pca(input_args);

output_args= result;


plotted_freqs=times;

end


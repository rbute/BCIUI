

addpath ..\JSON
settings=JSON.parse(fileread('..\..\settings\rbt2.json'));
fieldnames( settings.groupmodel);
group=getfield(settings.groupmodel,'gp2');
% cell2mat(group.frequencies(1,:));
freqs=cell2mat(group.frequencies)';
freqs=freqs(freqs~=1);
% addpath ..
% MyBot=bot(botsettings.botsettings);
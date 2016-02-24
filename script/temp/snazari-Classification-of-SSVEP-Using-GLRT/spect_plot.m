
% Data Generously Produced by
% Sam Nazari
% Jan 2016

load S2_mt
fftpoints = 8196;

%spect    = abs(fft(dat.de4,fftpoints));

freqBase = linspace(0,512,fftpoints)';

xlabel('Freq (Hz)');
ylabel('Voltage (\muV)');
title(['EEG Spectrum of O_z, freqs: [  ' num2str(dat.tf) '  ]']);

try
    myBot = bot()  ;
catch something
    disp('Bot Preparation Failed')
end

% session=session(randperm(length(session)),1);
% disp(session)

for i=1:6
    spect    = abs(fft(session{i,1},fftpoints));
    plot(freqBase(freqBase > 0 & freqBase < 30), spect(freqBase > 0 & freqBase < 30,:));
    xlabel('Freq (Hz)');
    ylabel('Voltage (\muV)');
    title(['EEG Spectrum of O_z, freqs: [  ' num2str(dat.tf) '  ] Detected->']);
    %     disp(acpow(spect,freqBase,dat.tf,0.5)')
    power_chart=acpow(spect,freqBase,dat.tf,0.5);
    [max_value,max_index] = max(power_chart);
    title(['EEG Spectrum of O_z, freqs: [  ' num2str(dat.tf) ...
        '  ] Detected-> ' num2str(dat.tf(max_index))]);
    disp(dat.tf(max_index))
    disp(max_index)
    pause(2);
end

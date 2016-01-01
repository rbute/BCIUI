signal = load('tremor_analysis.txt');
N = length(signal);
fs = 62.5; % 62.5 samples per second
fnyquist = fs/2; %Nyquist frequency

% plot(abs(fft(signal)))
% xlabel('Frequency (Bins - almost!)')
% ylabel('Magnitude');
% title('Double-sided Magnitude spectrum');
% axis tight

X_mags = abs(fft(signal));
bin_vals = [0 : N-1];
fax_Hz = bin_vals*fs/N;
N_2 = ceil(N/2);
plot(fax_Hz(1:N_2), X_mags(1:N_2))
xlabel('Frequency (Hz)')
ylabel('Magnitude');
title('Single-sided Magnitude spectrum (Hertz)');
axis tight
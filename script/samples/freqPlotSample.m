[x fs]=wavread('filename.wav');
dt=1/fs;%% time interval

X=fft(x);
df=1/(length(x)*dt); %% frequency interval
f=(1:length(X))*df;%% frequency vector
%% frequency domain plot, freq in hertz
figure
plot(f,abs(X))
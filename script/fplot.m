

hold off;
clf(gcf);
subplot(1,2,1);
plot(abs(fft(capturedData(1,:)-capturedData(3,:))));
subplot(1,2,2);
plot(abs(fft(capturedData(2,:)-capturedData(4,:))));

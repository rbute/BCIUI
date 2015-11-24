
% feature of this Scrip:
% Miniature script for implementation of 
% Data Acquisition and analysis,
% Algorithms would be implemented to main DAQ and A 
% Script

 
global gLCDoc 
global settings 
global gLCApp 
global gChans 

global gLatestBlock 
global gBlockSecsPerTick 
global gLatestTickInBlock 
global gChansData 
global gT 

global datafolder
global samplingStartTime
global SSVEP_CHANS
global spect;

%code for sampling Start
gLCDoc.StartSampling
samplingStartTime = currentTimeMillis;
%Little Delay. No script to place to
pause(1)

% Code for Stopping sampling and analysis
gLCDoc.StopSampling

data=cell2mat(gChansData)';
figure;

[channelCount,~]=size(data);

% Save data for future use
mkdir([datafolder,'\',samplingStartTime,'\']);
save([datafolder,'\',samplingStartTime,'\',samplingStartTime,'_data'],'gChans',...
    'samplingStartTime','data');

% Take pictures of graphs
for i = 1: channelCount
    plot(data(i,:));
    ylabel('Ampitude(Volts)');
    xlabel('Time (Seconds)')
    title(['Time domain signal from ',dataChannelInfo(i,:)]);
    saveas(gcf,[datafolder,'\',samplingStartTime,'\',samplingStartTime,...
        '_Channel-',dataChannelInfo(i,:),'.jpg']);
end



sp=fft(data(1,:));
spmod = abs(sp);
plot(spmod);



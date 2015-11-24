 

% For Lab chart application and it's launching
global gLCDoc 
global settings 
global gLCApp 
global gChans 

% For Acquired data by lab chart
global gLatestBlock 
global gBlockSecsPerTick 
global gLatestTickInBlock 
global gChansData 
global gT 

% For scripted Data processing
global datafolder
global samplingStartTime
global SSVEP_CHANS
global spect
global capturedData

global dataAnalysisInfo;
global dataChannelInfo

% data=cell2mat(gChansData)';
% mkdir([datafolder,'\',samplingStartTime,'\']);
% save([datafolder,'\',samplingStartTime,'\',samplingStartTime,'_data'],'gChans',...
%     'samplingStartTime','data');


gLCDoc.StopSampling

capturedData=cell2mat(gChansData)';
clf; % clear current figure

[channelCount,~]=size(capturedData);

% Save data for future use
mkdir([datafolder,'\',samplingStartTime,'\']);
save([datafolder,'\',samplingStartTime,'\',samplingStartTime,'_data'],...
    'gChans','samplingStartTime','capturedData','gBlockSecsPerTick');

load('exp1_info');
% Take pictures of graphs
for i = 1: channelCount
    plot(capturedData(i,:));
    ylabel('Ampitude(Volts)');
    xlabel('Time (Seconds)')
    title(['Time domain signal from ',dataChannelInfo(i,:)]);
    saveas(gcf,[datafolder,'\',samplingStartTime,'\',samplingStartTime,...
        '_Channel-',dataChannelInfo(i,:),'.jpg']);
end

close(gcf);



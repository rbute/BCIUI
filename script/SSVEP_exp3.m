

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
global myBot;
global indx;
global UISettings
data=cell2mat(gChansData);
mkdir([datafolder,'\',samplingStartTime,'\']);
save([datafolder,'\',samplingStartTime,'\',samplingStartTime,'_data'],'gChans',...
    'samplingStartTime','data','UISettings');


close(gcf);

% gLCDoc.StopSampling

capturedData=cell2mat(gChansData);
% capturedData=capturedData;
% clf; % clear current figure
close(gcf);
[channelCount,~]=size(capturedData);

% Save data for future use
% mkdir([datafolder,'\',samplingStartTime,'\']);
% save([datafolder,'\',samplingStartTime,'\',samplingStartTime,'_data.mat'],...
%     'gChans','samplingStartTime','capturedData','gBlockSecsPerTick','UISettings');
% disp(datafolder)

% load('exp1_info');
% % Take pictures of graphs
% for i = 1: channelCount
%     plot(capturedData(i,:));
%     ylabel('Ampitude(Volts)');
%     xlabel('Time (Seconds)')
%     title(['Time domain signal from ',dataChannelInfo(i,:)]);
%     saveas(gcf,[datafolder,'\',samplingStartTime,'\',samplingStartTime,...
%         '_Channel-',dataChannelInfo(i,:),'.jpg']);
% end
close(gcf);


%Copy essential Chunks from DBPlotter

% global gBlockSecsPerTick
global fftPoints
global f1
global f2
% global settings;
global gridrows;
global gridcols;
global tobeplottedchans;
global gridusedupto;
global settingsFilepath
global saveplot

global stimulusFreqs


f1 = 5.0;
f2 = 20.0;
gridrows=2;
gridcols=7;
saveplot=true;
gridusedupto=0;
fftPoints = 2048;
tobeplottedchans=1:6;
% if gBlockSecsPerTick=10e-2
    gBlockSecsPerTick=1e-2;
% end
settingsFilepath = '..\..\settings\robotControl.json';
stimulusFreqs = [7 9 13 15 17];

spectrum=abs(fft(data(:,[5]),fftPoints));
freqBase = linspace(0,fftPoints,1/gBlockSecsPerTick)';

% plot(freqBase(freqBase>f1 & freqBase < f2),spectrum(freqBase>f1 & freqBase < f2,:))

% Analysis2;
% if exist('myBot','var') && ~  isempty(myBot)  
%     disp('Sending Command to Bot');
%     myBot.sendCommand(num2str( indx));
% end


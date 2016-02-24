classdef LabChartObject
    %LABCHARTOBJECT Summary of this class goes here
    %   Detailed explanation goes here
    
    properties
        gLCDoc
        gLCApp
        oncleanup
        
        gLatestBlock
        gBlockSecsPerTick
        gLatestTickInBlock
        gChans
        gChansData
        gT
    end
    
    methods
        function this =   LabChartObject()
            
            this.gLCDoc = actxserver('ADIChart.Document');
            this.gLCApp = this.gLCDoc.Application;
            
            addlistener(this.gLCDoc,'OnStartSamplingBlock'  ,...
                this.OnBlockStart());
            addlistener(this.gLCDoc,'OnNewSamples'          ,...
                this.OnNewSamples());
            addlistener(this.gLCDoc,'OnFinishSamplingBlock' ,...
                this.OnBlockFinish());
            addlistener(this.gLCDoc,'OnSelectionChange'     ,...
                this.OnSelectionChange());
            
            %             this.gLCDoc.registerevent({
            %                 'OnStartSamplingBlock' @()OnBlockStart(this);
            %                 'OnNewSamples' OnNewSamples(this);
            %                 'OnFinishSamplingBlock' @()this.OnBlockFinish;
            %                 'OnSelectionChange' @()this.OnSelectionChange
            %                 });
            
            this.oncleanup=onCleanup(@()clear(this));
        end
        
        
        
        function clear(this)
            this.gLCApp.CloseActiveDocument(true);
            %             this.gLCApp.Quit;
        end
        
        function OnSelectionChange(this,varargin)
            %Example event handler. Called when the selection changes in the LabChart
            %document.
            %This example gets the selected data as a (2D) matrix with each column
            %corresponding to a channel and each row corresponding to a time point.
            this.gLCDoc;
            disp('OnSelectionChange called')
            kArrayOfDoubles = 1;  % GetSelectedData returns a matrix of doubles, not variants (cells)
            kAllSelectedChannels = -1; % GetSelectedData returns all selected channels, rather just the specified channel.
            % data = gLCDoc.GetSelectedData(kArrayOfDoubles, kAllSelectedChannels);
            % subplot(1,1,1); %no subplots
            % plot(data)
        end
        
        function varargin =  OnBlockStart(this,varargin)
            %Example event handler called when sampling and a new block is about to be
            %added to the document. This is called *before* the new block has been
            %added, so gLCDoc.NumberOfRecords does not yet include the new block.
            %Initialise the global variables used by OnNewSamples().
            %             global gLCDoc;
            %             global gLatestBlock;
            %             global gBlockSecsPerTick;
            %             global gLatestTickInBlock;
            %             global gChans;
            %             global gChansData;
            %             global gT;
            %disp('OnBlockStart called');
            this.gLatestBlock = this.gLCDoc.NumberOfRecords;
            this.gBlockSecsPerTick = this.gLCDoc.GetRecordSecsPerTick(this.gLatestBlock);
            disp(['OnBlockStart-> gLatestBlock: ',int2str(int64(this.gLatestBlock))...
                , ' gBlockSecsPerTick: ',int2str(int64(this.gBlockSecsPerTick))]);
            this.gNChanSamples = 0;
            this.gT = [];   %time (from start of block)
            this.gLatestTickInBlock = 0;
            
            %gChansData is a cell array because LabChart channels are not necessarily all the same
            %length while sampling, since the data can arrive in each channel with
            %different delays.
            this.gChansData = cell(size(this.gChans));
            
            
        end
        
        function OnNewSamples(this)
            %Example event handler called when sampling, whenever new samples might be
            %available, typically 20 times a second.
            %This example gets any new samples from channel gChan, appends them to
            %the gChan1Data vector, then plots the latest 5000 samples.
            %             global gLCDoc;
            %             global gLatestBlock;
            %             global gBlockSecsPerTick;
            %             global gLatestTickInBlock;
            %             global gChans;
            %             global gChansData;
            %             global gT;
            %disp('OnNewSamples called')
            this.gLatestTickInBlock = this.gLatestTickInBlock+double(varargin{3});
            % HRESULT GetChannelData([in]ChannelDataFlags flags, [in]long channelNumber, [in]long blockNumber, [in]long startSample, [in]long numSamples, [out,retval]VARIANT *data) const;
            % For the sampling case we can pass -1 for the number of samples parameter,
            % meaning return all available samples
            newSamplesMax = 0; %max new samples added across channels
            minChanLength = 1e30; %number of samples in the shortest channel
            for ch = this.gChans
                % HRESULT GetChannelData([in]ChannelDataFlags flags, [in]long channelNumber, [in]long blockNumber, [in]long startSample, [in]long numSamples, [out,retval]VARIANT *data) const;
                % For the sampling case we can pass -1 for the number of samples parameter,
                % meaning return all available samples
                chanSamples = this.gLCDoc.GetChannelData (1, ch, this.gLatestBlock+1, length(this.gChansData{ch})+1, -1);
                this.gChansData{ch} = [this.gChansData{ch}; chanSamples'];
                if minChanLength > length(this.gChansData{ch})
                    minChanLength = length(this.gChansData{ch});
                end
                if newSamplesMax < length(chanSamples)
                    newSamplesMax = length(chanSamples);
                end
            end
            
            if newSamplesMax > 0
                nSamples = length(this.gT);
                this.gT = [this.gT; [nSamples:nSamples+newSamplesMax-1]'*...
                    this.gBlockSecsPerTick];
            end
            
            %plot the latest 5000 samples
            plotRange = max(1,minChanLength-5000):minChanLength;
            % for ch = gChans
            %     subplot(length(gChans),1,ch), plot(gT(plotRange),gChansData{ch}(plotRange));
            % end
            varargout = [];
            
        end
        function OnBlockFinish(this,varargin)
            
            disp('OnBlockFinish-> ')
            %This would be a good place to save the gChansData and gT for the completed
            %block, if needed.
            
        end
        
    end
    
end


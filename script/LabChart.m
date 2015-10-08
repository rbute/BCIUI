%LABCHART Plots the output of ADInstruments LabChart Export MATLAB
%         extension 3.0 of LabChart 7.2 or later
%         This is version 1.1
%         For more information visit http://www.adinstruments.com/

clear all, close all

% Load *.mat file
[fname, pathname] = uigetfile('*.mat', 'Select a MAT-file');
if fname==0,
    return
end
load([pathname fname])

if ~exist('data','var'),
    error('No data! Select a mat file that contains data and was created with Export Matlab 3.0 or later (LabChart for Windows 7.2 or later)')
    return
end

[numchannels numblocks] = size(datastart);

%Set up figure, size, position
figure(1)
clf
FigName = ['LabChart - All Channels, all Blocks of ' fname ];
scrsz = get(0,'ScreenSize');
set(gcf,'Name',FigName, 'Position', scrsz([3 4 3 4]).*[1 1 6 6]/8);

% For all channels and all blocks...
for ch = 1:numchannels,
    for bl = 1:numblocks
        pdata = [];
        ptime = [];
        temp2 = [];
        
        if (datastart(ch,bl) ~= -1)%empty blocks excluded
            pdata = data(datastart(ch,bl):dataend(ch,bl));
            if exist('scaleunits','var')%16-bit data
                pdata = (pdata + scaleoffset(ch,bl)).* scaleunits(ch,bl);
            end
            ptime = [0:size(pdata,2)-1]/samplerate(ch,bl);
            subplot(numchannels,numblocks,(ch-1)*numblocks+bl)
            plot(ptime,pdata), hold on
            
            % Titles
            % shown on first block of each channel. If first block of a
            % channel is empty, no title will be shown
            if bl == 1
                title(titles(ch,:));
            end
            
            % Ranges and units
            % x-axis: always in seconds
            xlabel('Time (s)');
            if (length(ptime) ~= 1)%exclude blocks with only one data point
                xlim([0 max(ptime)])
            end
            
            % y-axis labels / units: always in base units
            if (unittextmap(ch,bl) ~= -1)
                unit = unittext(unittextmap(ch,bl),:);
                ylabel(unit);
            end
            
            if exist('scaleunits','var')%16-bit data
                pmax = (rangemax(ch,bl) + scaleoffset(ch,bl)) .* scaleunits(ch,bl);
                pmin = (rangemin(ch,bl) + scaleoffset(ch,bl)) .* scaleunits(ch,bl);
            else
                pmax = rangemax(ch,bl);%32-bit data
                pmin = rangemin(ch,bl);
            end
            ylim([pmin pmax]);
            
            if exist('com','var'),
                % Comments
                % Content of matrix com:
                % column 1: comchan (channel comment was made in, can be
                %           -1 for all ch)
                % column 2: comblock (block comment was made in)
                % column 3: comtickpos (position of comment referring to
                %           tickrate of that block)
                % column 4: comtype (comment type 1=user, 2=event marker)
                % column 5: comtextmap (contains index that refers to
                %           column vector comtext)
                
                % Find out if there is a comment for this block. There are two types:
                % 1. This channel only comments, (com(:,1) == ch)
                % 2. Comments across all channels (com(:,1) == -1)
                if (isfinite(find(com(:,2) == bl & (com(:,1) == ch | com(:,1) == -1))))
                    temp = find((com(:,2) == bl & (com(:,1) == ch | com(:,1) ==-1)));
                    comtickpos = com(:,3);
                    comtextmap = com(:,5);
                    for m = 1:size(temp)
                        temp2 = temp(m);
                        x = round(comtickpos(temp2)* ...        %convert from tick rate position
                            (samplerate(ch,bl)/tickrate(bl)));  %to sample rate position
                        t = x/samplerate(ch,bl);
                        plot([t t],[pmin pmax],'r:');
                        text(t, (pmin + 0.1*(pmax - pmin)),comtext(comtextmap(temp2),:), ...
                            'Rotation',90);
                    end
                end
            end
        end
    end
end
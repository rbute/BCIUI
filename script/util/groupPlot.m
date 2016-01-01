function groupPlot( data, timebase , grid, plots ,xtext,ytext,titletext)
%GROUPPLOT Summary of this function goes here
%   Detailed explanation goes here
%   groupPlot( data, timebase , grid, plots ,xtext,ytext,titletext)

[~,N]=size(data);
% size(data)
% close(gcf);

for i= 1:N
    subplot(grid(1),grid(2),plots(i));
    plot(timebase,data(:,i));
    
    if length(xtext)==1
        xlabel(xtext);
    elseif length(xtext)>1
        xlabel(xtext{i})
    end
    
    if length(ytext)==1
        ylabel(ytext)
    elseif length(ytext)>1
        ylabel(ytext{1})
    end
    
    if length(titletext)==1
        title(titletext)
    elseif length(titletext)>1
        title(titletext{i})
    end
%     [Peak, PeakIdx] = findpeaks(data(i,:));
%     disp(findpeaks(data(i,:)))
%     text(data(i,PeakIdx), Peak, sprintf('Peak = %6.3f', Peak));
end

end


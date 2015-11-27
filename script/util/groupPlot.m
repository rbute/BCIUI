function groupPlot( data, timebase , rows , cols ,xtext,ytext)
%GROUPPLOT Summary of this function goes here
%   Detailed explanation goes here
[N,~]=size(data);
close(gcf);

for i= 1:N
    subplot(rows,cols,i);
    plot(timebase,data(i,:));
    
    if isrow(xtext)
        xlabel(xtext);
    else
        xlabel(xtext(i))
    end
    
    if isrow(ytext)
        ylabel(ytext)
    else
        ylabel(ytext(i))
    end
    
end

end


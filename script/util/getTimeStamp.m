function output = getTimeStamp
%GETTIMESTAMP Summary of this function goes here
%   Detailed explanation goes here

datetime = clock;

output='';
for i= 1:5
    output=[output,int2str(int16(datetime(i))),'.'];    
end
 output=[output,int2str(int16(datetime(6)))];
end


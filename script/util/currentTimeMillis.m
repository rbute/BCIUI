function output = currentTimeMillis(  )
%CURRENTTIMEMILLIS Summary of this function goes here
%   Detailed explanation goes here

output=int2str(int64(java.lang.System.currentTimeMillis()));
end


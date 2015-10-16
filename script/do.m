function [ output_args ] = do( options,coordinates,actionCommand,calltime )
%DO Summary of this function goes here
%   Detailed explanation goes here

if strcmp(actionCommand,'setup')

    setupLC;

else if strcmp(actionCommand,'doit')
    disp('Just do it')
else if strcmp(actionCommand,'dontdoit')
    disp('Why dont you do it')
end

end


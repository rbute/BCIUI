function [ output_args ] = readGroupModels( input_args )
output_args=JSON.parse(fileread(filepath));
output_args=struct2cell(output_args.groupmodel);
end


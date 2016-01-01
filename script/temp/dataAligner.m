
database=uigetdir('..\data','Please select a Session');
% [dbname,foldername,~]=fileparts(database);
folders=struct2cell(dir(database))';
folders=folders(:,1);
[fl,~]=size(folders);
folders=folders(3:fl,1);
fl=fl-2;
% [database,'\',strtrim(folders(i,:)),'\',strtrim( folders(i,:)),'_data.mat']
% a=1;
%

for a=1:fl
   load([database,'\',folders{a},'\',folders{a},'_data.mat']); 
   capturedData=capturedData';
   save([database,'\',folders{a},'\',folders{a},'_data.mat'],...
    'gChans','samplingStartTime','capturedData'...,'gBlockSecsPerTick'...
    ); 
%     disp([database,'\',folders{a},'\',folders{a},'_data_1.mat'])
%     delete [database,'\',folders{a},'\',folders{a},'_data_1.mat']
end
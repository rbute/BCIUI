

close all;

database=uigetdir('..\data','Please select a Database');
folders=ls(database);
folders=folders(3:size(folders,1),:);
foldersList=cell(size(folders,1),1);


% try
%     myBot = bot('COM12',[],[]);
% catch
% end

for i=1:size(folders,1)
    if exist(strcat(database,'\' ,strtrim(folders(i,:))),'dir')
        foldersList{i}=strcat(database,'\' ,strtrim(folders(i,:)));
    end
end
foldersList= foldersList(~cellfun('isempty',foldersList));

pause(5);
for iCellRec=1:length(foldersList)
    disp(['Calling: ',foldersList{iCellRec}]);
    database=foldersList{iCellRec};
    DBPlotter
    pause(1);
end
% myBot.sendCommand('0');
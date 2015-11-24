
database=uigetdir('.\data','Please select a Database');
folders=ls(database);
[fl,~]=size(folders);
folders=folders(3:fl,:);
%clear fl;
fl=fl-2;
figure;
for i=1:fl
    disp(['Loading ',folders(i,:)])
    load([database,'\',folders(i,:),'\',folders(i,:),'_data.mat']);
    fplot;
    pause(0.750);
end
function [ output_args ] = get_ssvep_mod_mat( freqs , Fs , timeLen )
%get_ssvep_mod_mat( freqs , Fs , timeLen )
%   example:  get_ssvep_mod_mat( [ 2 3 4 5] , 1000 , 2 )
% Generates a ssvep charachterization matrix
% freqs in Hz
% Fs in Hz
% timeLen in seconds
Fs=double(Fs);
timeLen=double(timeLen);
[~,Nf]=size(freqs);
output_args=zeros(int32(timeLen*Fs),int32(2*Nf));

for i=1:Nf
    output_args(:,2*i-1) = sin(2*pi*freqs(i)*(0:1/Fs:(timeLen-1/Fs))');
    output_args(:,2*i)   = cos(2*pi*freqs(i)*(0:1/Fs:(timeLen-1/Fs))');
end

end
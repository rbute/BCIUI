

function y=acpow(DS,f,tf,win)

N=length(tf);
y=zeros(N,1);
for i=1:N
    rang = find(f>(tf(i)-win/2) & f<(tf(i)+win/2));
    startP=rang(1);
    endP=rang(end);
    
    if ((endP-startP+1)>0)
        y(i) = sum(DS(startP:endP))/(endP-startP+1);
    else
        y(i) = sum(DS(startP:endP));
    end
end
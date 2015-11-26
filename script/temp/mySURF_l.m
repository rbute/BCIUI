function SURF_feature=mySURF_l(image)

[H, W]=size(image);
integral_image=integralImage(image);
p=13;
padded_image=padarray(integral_image,[p,p]);
[m, n]=size(padded_image);
result_image_91=constrctnxn(padded_image,m,n,9,p);
result_image_151=constrctnxn(padded_image,m,n,15,p);
result_image_211=constrctnxn(padded_image,m,n,21,p);
% result_image_271=constrctnxn(padded_image,m,n,27,p);


kp=[];
kpl=[];
buff=0;
chck=zeros(H,W);
for i=3:H-1
    for j=3:W-1
        if(chck(i-l,j)==1 || chck(i-2,j)==1)
            continue;
        end
        
        if(buff>0)
            buff=buff-1;
            continue;
        end
        if result_image_151(i,j)>100
            x=result_image_91(i-1:i+1,j-1:j+1);
            y=result_image_151(i-1:i+1,j-1:j+1);
            z=result_image_211(i-1:i+1,j-1:j+1);
            %         y(1:4)=y(1:4);
            y(5:8)=y(6:9);
            mx=max(max(x));
            mz=max(max(z));
            mix=min(min(x));
            miz=min(min(z));
            my=max(max(y));
            miy=min(min(y));
            if (result_image_151(i,j)>my && result_image_151(i,j)>mz && result_image_151(i,j)>mx)
                kp=[kp result_image_151(i,j)];
                kpl=[kpl; i j];
                chck(i,j)=1;
                buff(i,j)=2;
            end
        else continue
        end
    end
end;
cnt2=1;
for i=1:size(kpl,1)
    if (kpl(i,1)>10 && kpl(i,1)<H-10 && kpl(i,2)>10 && kpl(i,2)<W-10)
        cor1(cnt2, :)=[kpl(i,:)];
        cnt2=cnt2+1;
    else continue
    end
end
s=15;
r=(s/9)*1.2;
figure, imshow(image);
hold on
plot(cor1(:,2), cor1(:,1), 'r.');
title('Image with key points mapped onto it');

h = fspecial('gaussian', 2*r, r);
filtr_image=imfilter(image,h);

integral_image_filter=integralImage(filtr_image);

for i=1:size(cor1)
    theta(i,:)=windw(integral_image_filter,r,cor1(i,:));
end
% xz=2*r;
% for i=5:H-4
%     for j=5:W-4
%         harres_x(i,j)=(sum(sum(image(i-r:i+r-1,j:j+r-1)))-sum(sum(image(i-r:i+r-1,j-r:j-1))))/(xz*xz);
%         harres_y(i,j)=(sum(sum(image(i-r+1:i,j-r+1:j+r)))-sum(sum(image(i+1:i+r,j-r+1:j+r))))/(xz*xz);
%
%     end;
% end;

for i1=1:size(cor1,1)
    tform = affine2d([cosd(theta(i1,1)) -sind(theta(i1,1)) 0; sind(theta(i1,1)) cosd(theta(i1,1)) 0; 0 0 1]);
    cnt1=1;
    for j=-round(9*r)-1:round(10*r)+1
        for k=-round(9*r)-1:round(10*r)+1
            
            reg1(cnt1,:)=[cor1(i1,1)+j cor1(i1,2)+k];
            
            cnt1=cnt1+1;
        end;
    end;
    for i2=1:size(reg1,1)
        reg2(i2,:)=[reg1(i2,:)-cor1(i1,:)];
    end;
    %reg2=flip(reg1,2);
    use_points=round(transformPointsForward(tform,reg2));
    
    for i2=1:size(use_points,1)
        reg3(i2,:)=[use_points(i2,:)+cor1(i1,:)];
    end;
    %use_points=flip(use_points2,2);
    %      for m1=1:size(use_points2,1)
    %          if use_points2(m1,1)<0 && use_points2(m,2)<0
    %              use_points(m1,:)=[-1*use_points2(m1,1)+20*r -1*use_points2(m1,2)+20*r];
    %          elseif use_points2(m1,1)>0 && use_points2(m,2)<0
    %              use_points(m1,:)=[use_points2(m1,1)+20*r -1*use_points2(m1,2)];
    %          elseif use_points2(m1,1)<0 && use_points2(m,2)>0
    %              use_points(m1,:)=[-1*use_points2(m1,1) use_points2(m1,2)+20*r];
    %          elseif use_points2(m1,1)>0 && use_points2(m,2)>0
    %              use_points(m1,:)=[use_points2(m1,1) use_points2(m1,2)];
    %          end;
    %      end;
    
    cnt2=1;
    for l1=1:20*r+1:size(reg3,1)
        for l2=1:20*r+1
            M(cnt2,l2)=image(reg3(l2+l1-1,1),reg3(l2+l1-1,2));
        end;
        cnt2=cnt2+1;
    end;
    
    M_pad=padarray(M,[r,r]);
    for i=3:size(M_pad,1)-r-1
        for j=3:size(M_pad,2)-r-1
            p(i-2,j-2)=(sum(sum(M_pad(i-r+1:i+r,j+1:j+r)))-sum(sum(M_pad(i-r+1:i+r,j-r+1:j))))/(2*r*2*r);
            q(i-2,j-2)=(sum(sum(M_pad(i-r+1:i,j-r+1:j+r)))-sum(sum(M_pad(i+1:i+r,j-r+1:j+r))))/(2*r*2*r);
        end
    end
    
    cnt3=1;
    for k1=1:4
        for j1=1:4
            p1=sum(sum(p(1+(k1-1)*5*r:k1*5*r,1+(j1-1)*5*r:j1*5*r)));
            q1=sum(sum(q(1+(k1-1)*5*r:k1*5*r,1+(j1-1)*5*r:j1*5*r)));
            p2=abs(sum(sum(p(1+(k1-1)*5*r:k1*5*r,1+(j1-1)*5*r:j1*5*r))));
            q2=abs(sum(sum(q(1+(k1-1)*5*r:k1*5*r,1+(j1-1)*5*r:j1*5*r))));
            des(1,cnt3:cnt3+3)=[p1 q1 p2 q2];
            cnt3=cnt3+4;
        end;
    end;
    descpt(i1,:)= des;
    
end
SURF_feature=[cor1 descpt];
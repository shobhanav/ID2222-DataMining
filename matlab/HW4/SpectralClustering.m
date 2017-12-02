clc;
%E = csvread('example1.dat');
E = csvread('example2.dat');

col1 = E(:,1);
col2 = E(:,2);
max_ids = max(max(col1,col2));
As= sparse(col1, col2, 1, max_ids, max_ids); 
A = full(As);



D = diag(sum(A,2));

L = (D^(-0.5))*A*(D^(-0.5));

k = 4;
[X,D] = eigs( L, k,'LM' );

% normalizing
Y = X./sqrt(sum(X.^2,2))

idx = kmeans(Y,k);

color = {'k','b','r','g','y'};

% plot 
figure,
hold on;
for i=1:size(A,1)
  c = idx(i,1);
  for j=1:size(A,2)  
    if A(i,j) == 1
        plot(i,j,'color', color{c}, 'marker', '+');
    end  
  end  
end
hold off;
title('Clustered Data');
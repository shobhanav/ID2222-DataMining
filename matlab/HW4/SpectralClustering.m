clc;
%E = csvread('example1.dat');
E = csvread('example2.dat');
k = 10;

col1 = E(:,1);
col2 = E(:,2);
max_ids = max(max(col1,col2));
As= sparse(col1, col2, 1, max_ids, max_ids); 
A = full(As);

G = graph(A,'OmitSelfLoops');
p = plot(G,'layout','force','Marker','.','MarkerSize',4.5);
title('Graph');
axis equal


D = diag(sum(A,2));
L = (D^(-0.5))*A*(D^(-0.5));

[X,D] = eigs( L, k, 'LM');

% normalizing
Y = X./sqrt(sum(X.^2,2));

fv = sort(X(:,1));
figure,
plot(fv,'+');
title('Sorted Fiedler Vector');

idx = kmeans(Y,k);

color = {'k','r','b','g','y'};

cc=hsv(12);
for i=1:k
    highlight(p,find(idx==i),'NodeColor',cc(i,:))
end

% plot 
figure,
hold on;
for i=1:size(A,1)
  c = idx(i,1);
  for j=1:size(A,2)  
    if A(i,j) == 1
        plot(i,j,'color', cc(c,:), 'marker', '+');
    end  
  end  
end
hold off;
title('Clustered Data');
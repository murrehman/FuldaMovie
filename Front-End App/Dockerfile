FROM node:16

WORKDIR /usr/src/app

COPY . .

RUN npm install
RUN npm install -g @angular/cli
RUN ng build --prod

EXPOSE 4200

CMD [ "node", "server.js"  ]

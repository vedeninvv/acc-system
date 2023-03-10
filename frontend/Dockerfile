FROM node:lts-alpine

RUN mkdir /app
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY . .
RUN npm install
RUN npm install -g @vue/cli

EXPOSE 3000

CMD ["npm", "run", "serve"]
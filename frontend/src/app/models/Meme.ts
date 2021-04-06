import { Tag } from "./Tag";

export interface Meme {
  id?: number;
  title: string;
  description?: string;
  imageblob?: Blob;
  likes?: number;
  dislikes?: number;
  imageSrc?: string;
  categoryId: number;
  userId: number;
  imageId?: number;
  tags?: Tag[];
  createdate?: Date;
};
